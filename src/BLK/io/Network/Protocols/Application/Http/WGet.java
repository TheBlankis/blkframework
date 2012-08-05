/*
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 */
package BLK.io.Network.Protocols.Application.Http;

import BLK.System.Logger;
import BLK.io.FileSystem.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
 *
 * @author The Blankis < blankitoracing@gmail.com >
 */
public class WGet {
    private URL url;
    private String contentType;
    private File destination;
    public WGet(URL url){this.url = url;}
    public URL getUrl(){return url;}
    public String getContentType(){return contentType;}
    public File getDestination(){return destination;}


    public boolean download()
    {
        try
        {
            byte[] buffer = new byte[4 * 1024];
            int read;

            URLConnection con;
            con = url.openConnection();
            con.connect();

            this.contentType = con.getContentType();
            this.destination = File.getTemp("dow");

            InputStream in = con.getInputStream();

            FileOutputStream w = this.destination.getOutputStream();


            while ((read = in.read(buffer)) > 0) 
                w.write(buffer, 0, read);

            w.close();
            in.close();

            return true;
        }
        catch (IOException ex)
        {
            Logger.getLogger().error(ex);
            return false;
        }

    }



    public static WGet easyDownload(String url)
    {
        try
        {
            WGet tmp = new WGet(new URL(url));

            if (tmp.download())
                return tmp;
            else
                return null;
            
        }
        catch (MalformedURLException ex)
        {
            Logger.getLogger().warn(ex);
            return null;
        }
    }

}
