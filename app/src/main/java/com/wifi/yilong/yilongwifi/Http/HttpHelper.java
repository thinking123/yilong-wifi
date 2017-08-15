package com.wifi.yilong.yilongwifi.Http;


import android.util.Base64;

import com.wifi.yilong.yilongwifi.Infrastructure.MyLog;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.Model.Location;
import com.wifi.yilong.yilongwifi.Model.OpeningTime;
import com.wifi.yilong.yilongwifi.Model.Review;
import com.wifi.yilong.yilongwifi.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpHelper {

    private static final int READTIMEOUT = 15000;
    private static final int CONNECTTIMEOUT = 15000;
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String UTF_8 = "UTF-8";

    private static void SetConnection(HttpURLConnection conn , String method)throws ProtocolException {
        conn.setReadTimeout(READTIMEOUT);
        conn.setConnectTimeout(CONNECTTIMEOUT);
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
    }

    private static void SetConnectionHeader(HttpURLConnection conn ,Map<String , String> headers){
        for(Map.Entry<String , String> entry : headers.entrySet()){
            conn.setRequestProperty(entry.getKey() , entry.getValue());
        }
    }
    private static void SetPostData(HttpURLConnection conn , String data) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os , UTF_8));
        writer.write(data);
        writer.flush();
        writer.close();
        os.close();
    }

    private static String GetPostData(HttpURLConnection conn) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer sb = new StringBuffer("");
        String line = "";

        while((line = in.readLine()) != null){
            sb.append(line);
        }
        in.close();

        return sb.toString();
    }

    public static String formatJSONToURLData(JSONObject data) throws JSONException, UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        Iterator<String> it = data.keys();

        while(it.hasNext()){
            String key = it.next();
            Object v = data.get(key);
            if(first){
                first = false;
            }else{
                sb.append("&");
            }

            sb.append(URLEncoder.encode(key , UTF_8));
            sb.append("=");
            sb.append(URLEncoder.encode(v.toString() , UTF_8));
        }

        return  sb.toString();


    }

    public static List<Location> GetLocations(String getUrl){
        return parseLocations(GetData(getUrl));
    }
    public static User SignUp(String postUrl , JSONObject js){
       return  parseUser(PostData(postUrl , js , null));
    }

    public static User Signin(String postUrl , JSONObject js){
        return parseUser(PostData(postUrl , js , null));
    }

    public static Review AddReview(String postUrl ,JSONObject js , Map<String , String> headers){
        return parseReview(PostData(postUrl , js ,headers));
    }
    public static Location GetLocationDetail(String getUrl) {
        return parseLocation(GetData(getUrl));
    }
    private static String readStream(InputStream stream, int maxLength) throws IOException {
        String result = null;
        // Read InputStream using the UTF-8 charset.
        InputStreamReader reader = new InputStreamReader(stream, UTF_8);
        // Create temporary buffer to hold Stream data with specified max length.
        char[] buffer = new char[maxLength];
        // Populate temporary buffer with Stream data.
        int numChars = 0;
        int readSize = 0;
        while (numChars < maxLength && readSize != -1) {
            numChars += readSize;
            int pct = (100 * numChars) / maxLength;
            readSize = reader.read(buffer, numChars, buffer.length - numChars);
        }
        if (numChars != -1) {
            // The stream was not empty.
            // Create String that is actual length of response body if actual length was less than
            // max length.
            numChars = Math.min(numChars, maxLength);
            result = new String(buffer, 0, numChars);
        }
        return result;
    }

    public static String GetData(String getUrl){

        InputStream stream = null;
        HttpURLConnection connection = null;
        String result = null;

        try {
//            getUrl = URLEncoder.encode(getUrl , UTF_8);
            URL url = new URL(getUrl);
            connection = (HttpURLConnection) url.openConnection();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

//            SetConnection(connection , GET);
//            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return result;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            result = new String(out.toByteArray() , UTF_8);
        }catch (Exception e){
            MyLog.Debug(String.format("Http failure : %s" , e.getMessage()));
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }
    public static String PostData(String postUrl , JSONObject js , Map<String , String> headers){
        String res="";

        try{
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            SetConnection(conn , POST);
            if(headers != null){
                SetConnectionHeader(conn , headers);
            }
            SetPostData(conn , formatJSONToURLData(js));

            int resCode = conn.getResponseCode();
            if(resCode == HttpURLConnection.HTTP_OK){
                res = GetPostData(conn);
            }else{
                MyLog.Debug(String.format("Http failure ,  post code : %d" , resCode));
            }

            conn.disconnect();
        }
        catch (Exception e){
            MyLog.Debug(String.format("Post Data failure"));

        }



        return res;
    }

    private static String DecodeBase64(String src){
        try {
            byte[] srcBytes = src.getBytes(UTF_8);
            return new String(Base64.decode(srcBytes , Base64.DEFAULT) , UTF_8);
        } catch (UnsupportedEncodingException e) {
            MyLog.Debug(String.format("Base64 decode failure : %s" , e.getMessage()));
        }

        return "";
    }
    private static Location parseLocation(String src){
        Location lc = new Location();

        try {
            JSONObject js = new JSONObject(src);

            lc.setAddress(js.getString(Location.ADDRESS));
            lc.setName(js.getString(Location.NAME));
            lc.setId(js.getString(Location.ID));
            lc.setRating(Integer.parseInt(js.getString(Location.RATING)));

            //facility
            JSONArray facs = js.getJSONArray(Location.FACILITIES);
            List<String> facilities = new ArrayList<String>();
            if(facs != null){
                for(int j = 0 ; j < facs.length() ; j++){
                    facilities.add(facs.getString(j));
                }
            }
            lc.setFacilities(facilities);

            //opening times
            JSONArray openTimeArray = js.getJSONArray(Location.OPENINGTIMES);
            List<OpeningTime> openingTimes = new ArrayList<OpeningTime>();
            if(openTimeArray != null){
                for(int i = 0 ; i < openTimeArray.length() ; i++){
                    JSONObject openTime = openTimeArray.getJSONObject(i);
                    OpeningTime obj = new OpeningTime();
                    if(openTime.has(OpeningTime.CLOSED)){
                        obj.setClosed(Boolean.parseBoolean(openTime.getString(OpeningTime.CLOSED)));
                    }
                    if(openTime.has(OpeningTime.CLOSING)){
                        openTime.getString(OpeningTime.CLOSING);
                    }
                    if(openTime.has(OpeningTime.DAYS)){
                        openTime.getString(OpeningTime.DAYS);
                    }
                    if(openTime.has(OpeningTime.OPENING)){
                        openTime.getString(OpeningTime.OPENING);
                    }
                    openingTimes.add(obj);

                }
            }
            lc.setOpeningTimes(openingTimes);

            //reviews
            JSONArray reviewsArray = js.getJSONArray(Location.REVIEWS);
            List<Review> reviews = new ArrayList<Review>();
            if(reviewsArray != null){
                for(int i = 0 ; i < reviewsArray.length() ; i++){
                    JSONObject review = reviewsArray.getJSONObject(i);
                    //iew(String author, Date createdOn, int rating, String reviewText) {
                    Review obj = new Review(
                            review.getString(Review.AUTHOR),
                            Utils.parseDate(review.getString(Review.CREATEDON)),
                            review.getString(Review.ID),
                            Integer.parseInt(review.getString(Review.RATING)),
                            review.getString(Review.REVIEWTEXT),
                            Utils.parseDate(review.getString(Review.TIMESTAMP)));
                    reviews.add(obj);

                }
            }

            lc.setReviews(reviews);

            JSONArray coordsArray = js.getJSONArray(Location.COORDS);
            lc.setLatitude(Float.parseFloat(coordsArray.getString(0)));
            lc.setLongitude(Float.parseFloat(coordsArray.getString(1)));

        } catch (JSONException e) {
            MyLog.Debug(String.format("Json parse failure : %s" , e.getMessage()));
        }

        return lc;
    }
    public static List<Location> parseLocations(String src){
        List<Location> lcs = new ArrayList<Location>();

        try {
            JSONArray jarr = new JSONArray(src);
            for(int i = 0 ; i < jarr.length() ; i++){
                JSONObject js = jarr.getJSONObject(i);
                Location lc = new Location();
                lc.setAddress(js.getString(Location.ADDRESS));
                lc.setName(js.getString(Location.NAME));
                lc.setDistance(Float.parseFloat(js.getString(Location.DISTANCE)));
                lc.setId(js.getString(Location.ID));
                lc.setRating(Integer.parseInt(js.getString(Location.RATING)));
                JSONArray facs = js.getJSONArray(Location.FACILITIES);
                List<String> facilities = new ArrayList<String>();
                if(facs != null){
                    for(int j = 0 ; j < facs.length() ; j++){
                        facilities.add(facs.getString(j));
                    }
                }

                lc.setFacilities(facilities);



                lcs.add(lc);
            }
        } catch (JSONException e) {
            MyLog.Debug(String.format("Json parse failure : %s" , e.getMessage()));
        }
        return  lcs;
    }
    public static Review parseReview(String src){
        Review review = new Review();

        return review;
    }
    public static User parseUser(String src){
        User user = null;

        try {
            JSONObject jsToken = new JSONObject(src);
            String token = jsToken.getString(User.JSON_TOKEN);
            String[] jsonToken = token.split("\\.");
            if (jsonToken.length != 3) {
                return null;
            }


            JSONObject payload = new JSONObject(DecodeBase64(jsonToken[1]));
            user = new User();
            user.setId(payload.getString(User.JSON_ID));
            user.setEmail(payload.getString(User.JSON_EMAIL));
            user.setExpiration(new Date(Integer.parseInt(payload.getString(User.JSON_EXPIRATION))));
            user.setToken(src);

        } catch (JSONException e) {
            MyLog.Debug(String.format("Json parse failure : %s" , e.getMessage()));
        }

        return user;
    }
//	public static HttpResponse Post(String url , Map<String, String> parms) throws IOException{
//		DefaultHttpClient client = new DefaultHttpClient();
//
//		HttpPost httpPost = new HttpPost(url);
//
//		JSONObject holder = getJsonObjectFromMap(parms);
//		StringEntity se = new StringEntity(holder.toString());
//
//	}
//	byte[] getUrlBytes(String urlSpec) throws IOException{

	
	
	
	
	//		URL url = new URL(urlSpec);
//		
//		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//		
//		try{
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			InputStream in = conn.getInputStream();
//			
//			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
//				return null;
//			}
//		}finally{
//			conn.disconnect();
//		}
//		
//	}
}
