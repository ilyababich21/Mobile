package com.example.nivaserviceandroid.viewmodel.modbus;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Modbus {
    private final byte[] readBuffer = new byte[45];
    public OutputStream outputStream;
    public InputStream inputStream;

    public class MBResponse {
        public byte[] Raw;
        public int SlaveID;
        public int FuncCode;
    }

    public class MBRegResponse extends MBResponse {
        public int[] Data16;
    }

    public class MBBoolResponse extends MBResponse {
        public boolean[] DataBoolean;
    }

    public class MBExceptionResponse extends MBResponse {
        public int Exception;
    }

    public int ResponsesValid = 0;
    public int ResponsesError = 0;
    public int ResponsesTimeout = 0;


    public synchronized void ReadHoldingRegisters(int slaveID, int regStart, int regCount) throws IOException {
        final byte FuncCode = 0x03;
        ReadRegisters(FuncCode, slaveID, regStart, regCount);
    }

    public synchronized void ReadInputRegisters(int slaveID, int regStart, int regCount) throws IOException {
        final byte FuncCode = 0x04;
        ReadRegisters(FuncCode, slaveID, regStart, regCount);
    }

    private void ReadRegisters(byte funcCode, int slaveID, int regStart, int regCount) throws IOException {
        byte[] reqPDU = new byte[5];
        // Build Request PDU
        reqPDU[0] = funcCode;
        reqPDU[1] = (byte) ((regStart) >> 8);
        reqPDU[2] = (byte) (regStart);
        reqPDU[3] = (byte) (regCount >> 8);
        reqPDU[4] = (byte) (regCount);
        sendPDU(slaveID, reqPDU);
    }

    public synchronized void WriteHoldingRegisters(int slaveID, int regStart, int[] regValues) throws IOException {
        byte FuncCode = 0x11;
        int regCount = regValues.length;
        byte[] reqPDU = new byte[regCount * 2 + 6];
        // Build Request PDU
        reqPDU[0] = FuncCode;
        reqPDU[1] = (byte) ((regStart - 1) >> 8);
        reqPDU[2] = (byte) (regStart - 1);
        reqPDU[3] = (byte) (regCount >> 8);
        reqPDU[4] = (byte) (regCount);
        reqPDU[5] = (byte) (regCount * 2);
        for (int i = 0; i < regCount; i++) {
            reqPDU[6 + i * 2] = (byte) (regValues[i] >> 8);
            reqPDU[7 + i * 2] = (byte) (regValues[i]);
        }
        sendPDU(slaveID, reqPDU);
    }

    protected void sendPDU(int slaveID, byte[] PDU) throws IOException {
        byte[] query = new byte[PDU.length + 1];
        query[0] = (byte) slaveID;
        System.arraycopy(PDU, 0, query, 1, PDU.length);
        outputStream.write(AddCRC(query));
    }


    public MBResponse readPDU() throws IOException {
        final int timeoutMillis = 500;
        MBResponse aResponse = new MBResponse();
        int byteCount = inputStream.read(readBuffer, 0, readBuffer.length); // readNBytes check

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < readBuffer.length; i++)
            sb.append(String.format("%02x ", (int) readBuffer[i]));
        Log.d("MyLog", "Response: " + sb);

        if (byteCount == 0 || byteCount < 3) {
            ResponsesTimeout++;
        } else {
            byte[] checkPacket = new byte[byteCount - 2];
            System.arraycopy(readBuffer, 0, checkPacket, 0, byteCount - 2);
            if (byteCount < 5) {
                ResponsesError++;
            } else {
                int rCRC = getCRC(checkPacket);
                if (((byte) rCRC == readBuffer[byteCount - 2])
                        & ((byte) (rCRC >> 8) == readBuffer[byteCount - 1])) {
                    ResponsesValid++;
                    aResponse = ProcessResponsePDU(byteCount);
                } else {
                    ResponsesError++;
                }
            }
        }
        return aResponse;
    }

    public int[] ReadRegPDU() throws IOException {
        int[] result = {};

        MBResponse aResponse = readPDU();

        if (aResponse instanceof MBRegResponse) {
            MBRegResponse aRegResponse = (MBRegResponse) aResponse;
            result = aRegResponse.Data16;
        }

        return result;

    }

    private MBResponse ProcessResponsePDU(int byteCount) {
        MBResponse nResponse;

        if (readBuffer[1] > 0x80) {
            MBExceptionResponse eResponse = new MBExceptionResponse();
            eResponse.FuncCode = (int) readBuffer[1] & 0xFF - 0x80;
            eResponse.Exception = (int) readBuffer[2] & 0xFF;
            nResponse = eResponse;
        } else {
            switch (readBuffer[1]) {
                case 0x03:
                case 0x04:
                    MBRegResponse rResponse = new MBRegResponse();
                    int regCount = (readBuffer[2] & 0xFF) / 2;
                    rResponse.Data16 = new int[regCount];
                    for (int i = 0; i < regCount; i++) {
                        rResponse.Data16[i] = (((int) readBuffer[3 + i * 2] & 0xff) << 8)
                                | ((int) readBuffer[4 + i * 2] & 0xff);
                    }
                    nResponse = rResponse;
                    break;
                default:
                    nResponse = new MBResponse();
            }
        }
        nResponse.SlaveID = readBuffer[0];
        nResponse.Raw = new byte[byteCount];
        System.arraycopy(readBuffer, 0, nResponse.Raw, 0, byteCount);

        return nResponse;
    }

    private byte[] AddCRC(byte[] input) {
        byte[] result = new byte[input.length + 2];
        int crc = getCRC(input);
        System.arraycopy(input, 0, result, 0, input.length);
        result[input.length] = (byte) (crc);
        result[input.length + 1] = (byte) (crc >> 8);
        return result;
    }

    private int getCRC(byte[] input) {
        int iPos = 0;
        int crc = 0xFFFF;
        while (iPos < input.length) {
            crc ^= (input[iPos] & 0xFF);
            iPos++;
            for (int j = 0; j < 8; j++) {
                boolean bitOne = ((crc & 0x1) == 0x1);
                crc >>>= 1;
                if (bitOne) {
                    crc ^= 0x0000A001;
                }
            }
        }
        return crc;
    }
}
