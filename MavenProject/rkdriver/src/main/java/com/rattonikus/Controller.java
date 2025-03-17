package com.rattonikus;

import org.usb4java.*; 

public class Controller {

    public Controller()
    {

    }

    public void start()
    {
        System.out.println("hello world");
        System.out.println(findAllDevice());
    }
    

    private short findAllDevice()
    {

        //read device list 
        Context context = new Context(); 
        int resuult = LibUsb.init(context);

        DeviceList list = new DeviceList(); 
        int result = LibUsb.getDeviceList(context, list);
        if (result < 0 ) throw new LibUsbException("unable to get list", result);


        try 
        {
            for(Device device: list)
            {
                DeviceDescriptor descriptor = new DeviceDescriptor(); 
                result = LibUsb.getDeviceDescriptor(device, descriptor);
                if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to read descriptor", result);
                //if (descriptor.idVendor() == vendorId && descriptor.idProduct() == productId) return device;
                return descriptor.iProduct();
                
            }
        }
        finally
        {
            LibUsb.freeDeviceList(list, true);
        }
        return (Short) null; 
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

    private int listDevice()
    {
        Context context = new Context(); 
        int result = LibUsb.init(context);
        DeviceList list = new DeviceList();
        int endresult = LibUsb.getDeviceList(context, list);
        for (Device device: list) {
            DeviceDescriptor descriptor = new DeviceDescriptor(); 
            result = LibUsb.getDeviceDescriptor(device, descriptor);
            return result;
        }
        return 69; 
    
    }

    private Device findDevice()
    {

        //read device list 
        Context context = new Context(); 
        int resuult = LibUsb.init(context);

        DeviceList list = new DeviceList(); 
        int result = LibUsb.getDeviceList(context, list);
        if (result < 0 ) throw new LibUsbException("unable to get list", result);


        try 
        {
            for(Device device: list)
            {
                DeviceDescriptor descriptor = new DeviceDescriptor(); 
                result = LibUsb.getDeviceDescriptor(device, descriptor);
                if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to read descriptor", result);
                //if (descriptor.idVendor() == vendorId && descriptor.idProduct() == productId) return device;
                return device;
                
            }
        }
        finally
        {
            LibUsb.freeDeviceList(list, true);
        }
        return null; 
    }
    

}
