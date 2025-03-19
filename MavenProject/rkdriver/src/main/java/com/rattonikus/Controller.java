package com.rattonikus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

import org.usb4java.*; 

public class Controller {

    public Controller()
    {

    }

    public void start()
    {

        // 336 Keyb ProdID as short 9610 VendID as short

        Short prodId = 336; 
        Short VendID = 9610; 

        System.out.println(findAllDevice());
        System.out.println("libusb starting");
        handleTest(findDevice(VendID, prodId));
        readVolume();
    }
    

    private void readVolume()
    {
        try 
        {
            Process pb = new ProcessBuilder("wpctl", "get-volume", "@DEFAULT_AUDIO_SINK@").start();
            String pbOutput = new String(pb.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            
            System.out.println("Output: " + pbOutput);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
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
                //Unline when i do lsusb in linux, this returns vendor and product as a *short*, linux prints it out as a hex. 
                DeviceDescriptor descriptor = new DeviceDescriptor(); 
                result = LibUsb.getDeviceDescriptor(device, descriptor);
                
                
                System.out.println("Device " +  Integer.toHexString(descriptor.idProduct()) + " " + descriptor.idProduct() + "< PROD VEND >" + Integer.toHexString(descriptor.idVendor()) + " " + descriptor.idVendor());             
            }
        }
        finally
        {
        }

        return 2;
    
    }
    

//    private String parsePipeWire()
  //  {
    //}



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


    private void handleTest(Device device)
    {

        DeviceHandle handle = new DeviceHandle(); 
        int result = LibUsb.open(device, handle);
        if (result != LibUsb.SUCCESS) throw new LibUsbException("unable to open deviceeee", result);


        try 
        {
            int interfaceResult = LibUsb.claimInterface(handle, 1);
            System.out.println("interface grabbed");
        }
        finally
        {
            LibUsb.close(handle);
        }

    }

    private Device findDevice(short vendorId, short productId)
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
                if (descriptor.idVendor() == vendorId && descriptor.idProduct() == productId) return device;
                return device;
            }
        }
        finally
        {
            System.out.println("free");
        }

        return null;
    }
}  
