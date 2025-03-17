package com.rattonikus;

import org.usb4java.*; 

public class Controller {

    public Controller()
    {

    }

    public void start()
    {
        System.out.println("hello world");
        startLibUsb();
    }

    private void startLibUsb() 
    {
        Context context = new Context(); 
        int result = LibUsb.init(context);

        if (result != LibUsb.SUCCESS) throw new LibUsbException("LIBUSB was unable to initiate", result);
        else{
            System.out.println("Libsub success"); 
        }
        
        LibUsb.exit(context);
        System.out.println("Disconnect"); 
    }
    

}
