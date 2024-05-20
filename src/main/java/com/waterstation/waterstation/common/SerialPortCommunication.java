//package com.waterstation.waterstation.common;
//
//import gnu.io.CommPort;
//import gnu.io.CommPortIdentifier;
//import gnu.io.SerialPort;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//public class SerialPortCommunication {
//
//    public static void main(String[] args) {
//        try {
//            // 获取串口端口标识符
//            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM1");
//
//            // 打开串口
//            CommPort commPort = portIdentifier.open("SerialPortExample", 2000);
//
//            if (commPort instanceof SerialPort) {
//                SerialPort serialPort = (SerialPort) commPort;
//
//                // 设置串口参数
//                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//
//                // 获取输入流和输出流
//                InputStream inputStream = serialPort.getInputStream();
//                OutputStream outputStream = serialPort.getOutputStream();
//
//                // 发送数据
//                outputStream.write("Hello, Serial Port!".getBytes());
//
//                // 读取数据
//                int data;
//                while ((data = inputStream.read())!= -1) {
//                    System.out.print((char) data);
//                }
//
//                // 关闭资源
//                inputStream.close();
//                outputStream.close();
//                serialPort.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
