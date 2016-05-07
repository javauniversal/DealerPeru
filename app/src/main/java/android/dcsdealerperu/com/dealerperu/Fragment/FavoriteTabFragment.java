package android.dcsdealerperu.com.dealerperu.Fragment;

import android.annotation.SuppressLint;
import android.dcsdealerperu.com.dealerperu.Adapter.AppAdapterRutero;
import android.dcsdealerperu.com.dealerperu.Entry.ListHome;
import android.dcsdealerperu.com.dealerperu.R;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

@SuppressLint("ValidFragment")
public class FavoriteTabFragment extends BaseVolleyFragment {

    private int mPosition;
    private AppAdapterRutero appAdapterRutero;
    private SwipeMenuListView mListView;

    public FavoriteTabFragment(int position) {
        mPosition = position;
    }

    public FavoriteTabFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rutero_menu, container, false);
        mListView = (SwipeMenuListView) rootView.findViewById(R.id.listView);

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupGrid();

    }

    private void llenarData(ListHome listHome) {
        appAdapterRutero = new AppAdapterRutero(getActivity(), listHome);
        mListView.setAdapter(appAdapterRutero);
    }

    private void setupGrid() {

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "rutero");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(final String response) {
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        String error_string = "";

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            error_string = "Error de tiempo de espera";
                        } else if (error instanceof AuthFailureError) {
                            error_string = "Error Servidor";
                        } else if (error instanceof ServerError) {
                            error_string = "Server Error";
                        } else if (error instanceof NetworkError) {
                            error_string = "Error de red";
                        } else if (error instanceof ParseError) {
                            error_string = "Error al serializar los datos";
                        }

                        onConnectionFailed(error_string);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSON(String response) {

        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                ListHome listHome = gson.fromJson(response, ListHome.class);
                llenarData(listHome);

            }  catch (IllegalStateException ex) {
                ex.printStackTrace();
            } finally {
            }
        } else {
        }

    }
}