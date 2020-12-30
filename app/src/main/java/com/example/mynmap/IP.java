package com.example.mynmap;

import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IP {
    public String getMyIPAddress() throws SocketException {
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
            NetworkInterface intf = en.nextElement();
            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                InetAddress inetAddress = enumIpAddr.nextElement();

                if(inetAddress.isLoopbackAddress()) {
                    Log.i("IPAddress", intf.getDisplayName() + "(loopback) | " + inetAddress.getHostAddress());
                }
                else {
                    Log.i("IPAddress", intf.getDisplayName() + " | " + inetAddress.getHostAddress());
                }

                if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                    return inetAddress.getHostAddress().toString();
                }
            }
        }
        return null;
    }

}
