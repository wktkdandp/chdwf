package com.example.myapplicationasdf.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.firebase.auth.FirebaseAuth;

import com.example.myapplicationasdf.MainActivity;
import com.example.myapplicationasdf.R;
import com.example.myapplicationasdf.TabActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth ;
    private HomeViewModel homeViewModel;
    EditText edit;
    TextView text;
    Button btn;
    XmlPullParser xpp;
    String key="cd45cf16e9c0413f8331e58a849612f1";
    String data;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        edit = (EditText) v.findViewById(R.id.edit12);
        text = (TextView) v.findViewById(R.id.result12);
        btn = (Button)v.findViewById(R.id.search_btn);
        btn.setOnClickListener(this::mOnClick);
        return v;
    }

        public void mOnClick (View v){
        switch (v.getId()) {
            case R.id.search_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        data = getXmlData();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(data);
                            }
                        });
                    }
                }).start();
//                ???????????? ??????
                break;
        }
    }

    String getXmlData(){
        StringBuffer buffer=new StringBuffer();
        String str= edit.getText().toString();
//        String location = URLEncoder.encode(str);

        String queryUrl="https://openapi.gg.go.kr/Childwelfarefaclt?KEY=7e60392bfd5b411ba7804bafc20bd3b7&Type=xml&pIndex=1&SIGUN_NM="+str;
        try{
            URL url= new URL(queryUrl);//???????????? ??? ?????? url??? URL ????????? ??????.
            InputStream is= url.openStream(); //url????????? ??????????????? ??????

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );
            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("?????? ??????...\n\n");

                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();

                        if(tag.equals("row")) ;
                        else if(tag.equals("FACLT_DIV_NM")){
                            buffer.append("???????????? : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("SIGUN_NM")){
                            buffer.append("????????? :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("BIZPLC_NM")){
                            buffer.append("???????????? :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("LICENSG_DE")){
                            buffer.append("??????????????? :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("BSN_STATE_NM")){
                            buffer.append("???????????? :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("  ,  ");
                        }
                        else if(tag.equals("ENTRNC_PSN_CAPA")){
                            buffer.append("????????????(???) :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("QUALFCTN_POSESN_PSN_CNT")){
                            buffer.append("?????????????????????(???) :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("TOT_PSN_CNT")){
                            buffer.append("????????????(???) :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("REFINE_LOTNO_ADDR")){
                            buffer.append("???????????? :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }

                        else if(tag.equals("REFINE_ZIP_CD")){
                            buffer.append("???????????? :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                            buffer.append("?????????????????????????????????????????????????????????????????????????????????");
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();

                        if(tag.equals("row")) buffer.append("\n");// ?????? ???

                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return buffer.toString();

    }

    }


