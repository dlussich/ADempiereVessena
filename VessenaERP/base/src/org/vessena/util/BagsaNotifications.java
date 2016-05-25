/**
 * 
 */
package org.openup.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.compiere.util.DB;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

/**
 * @author Emilino 03-09-2015
 *
 */
public class BagsaNotifications {

	/**
	 * 
	 */
	public BagsaNotifications() {};
		
		public void gcmSender(String notice){
			String ids = getIds();
			if(!"".equals(ids)){
				List<String> items = Arrays.asList(ids.split(","));
		        int tam = items.size();
		        try {

		            Sender sender = new Sender("AIzaSyAPNXxl1rekx4ZE7hg2avq0acdM3dcIZfI");

		            ArrayList<String> devicesList = new ArrayList<String>();

		            for(int i = 0; i<tam; i++){
		                devicesList.add(items.get(i));
		            }

		            // use this line to send message with payload data
		            Message message = new Message.Builder()
		                    .collapseKey("1")
		                    .addData("msg", notice)
		                    .build();

		            // Use this for multicast messages
		            MulticastResult result = sender.send(message, devicesList, 1);
		            System.out.println(result.toString());
		            if (result.getResults() != null) {
		                int canonicalRegId = result.getCanonicalIds();
		                if (canonicalRegId != 0) {
		                }
		            } else {
		                int error = result.getFailure();
		                System.out.println(error);
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			} 
	    }
	
	
	public String getIds(){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String res = "";
	    try{
	        pstmt = DB.prepareStatement("SELECT distinct tokenid from uy_bg_gcmdevice", null);
	           rs = pstmt.executeQuery();	           
	           if(rs!=null){
	        	   while (rs.next()) {
	        		  res = res + rs.getString(1) + ","; 
	        	  }
	           }
	           pstmt.close();
		    }catch(Exception e){
		        e.getMessage();
		    }finally{
		    	try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }
	    return res;
	}
	
	
	public String getIdGanador(int adUsr){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String res = "";
		
		String qry = "Select d.tokenid from AD_User u JOIN UY_UserReq ur" +
							" ON u.name = ur.code" +
						" JOIN UY_BG_GCMDevice d" +
							" ON d.deviceid = ur.deviceid" +
						" where u.ad_user_id = " + adUsr;
		
	    try{
	        pstmt = DB.prepareStatement(qry, null);
	           rs = pstmt.executeQuery();	           
	           if(rs!=null){
	        	   while (rs.next()) {
	        		  res = res + rs.getString(1) + ","; 
	        	  }
	           }
	           pstmt.close();
		    }catch(Exception e){
		        e.getMessage();
		    }finally{
		    	try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }
	    return res;
	}
	
	public void gcmSenderGanador(String notice, int adUsr){
		String ids = getIdGanador(adUsr);
        List<String> items = Arrays.asList(ids.split(","));
        int tam = items.size();
        try {

            Sender sender = new Sender("AIzaSyAPNXxl1rekx4ZE7hg2avq0acdM3dcIZfI");

            ArrayList<String> devicesList = new ArrayList<String>();

            for(int i = 0; i<tam; i++){
                devicesList.add(items.get(i));
            }

            // use this line to send message with payload data
            Message message = new Message.Builder()
                    .collapseKey("1")
                    .addData("msg", notice)
                    .build();

            // Use this for multicast messages
            MulticastResult result = sender.send(message, devicesList, 1);
            System.out.println(result.toString());
            if (result.getResults() != null) {
                int canonicalRegId = result.getCanonicalIds();
                if (canonicalRegId != 0) {
                }
            } else {
                int error = result.getFailure();
                System.out.println(error);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
