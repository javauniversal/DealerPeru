package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.ViewPagerAdapter;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasCombos;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.dcsdealerperu.com.dealerperu.R;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ActDetalleProducto extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    ViewPager viewPager;
    PagerAdapter adapter;
    private LinearLayout pager_indicator;
    private Bundle bundle;
    private ReferenciasCombos mDescribable = new ReferenciasCombos();
    private ImageView[] dots;
    private int dotsCount;
    //private ImageButton btnNext, btnFinish;

    public TextView txtReferencia;
    public TextView txtValorR;
    public TextView txtTaPantalla;
    public TextView txtCamara;
    public TextView txtFlash;
    public TextView txtCamaraFron;
    public TextView txtMemoria;
    public TextView txtBateria;
    private DecimalFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // To make activity full screen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (ReferenciasCombos)bundle.getSerializable("value");
        }

        format = new DecimalFormat("#,###.##");

        txtReferencia = (TextView) findViewById(R.id.txtNombre);
        txtValorR = (TextView) findViewById(R.id.txtPecioP);
        txtTaPantalla = (TextView) findViewById(R.id.txtPantalla);
        txtCamara = (TextView) findViewById(R.id.txtCamara);
        //txtFlash = (TextView) findViewById(R.id.txtFlash);
        txtCamaraFron = (TextView) findViewById(R.id.txtCamaraFrontal);
        txtMemoria = (TextView) findViewById(R.id.txtMemoria);
        //txtBateria = (TextView) findViewById(R.id.txtBateria);


        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        /*btnNext = (ImageButton) findViewById(R.id.btn_next);
        btnFinish = (ImageButton) findViewById(R.id.btn_finish);

        btnNext.setOnClickListener(this);
        btnFinish.setOnClickListener(this);*/

        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
        // Pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(ActDetalleProducto.this, mDescribable);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(this);

        setUiPageViewController();


        txtReferencia.setText(mDescribable.getDescripcion());
        txtValorR.setText(String.format("Precio S/. %s", format.format(mDescribable.getPrecio_referencia())));
        txtTaPantalla.setText(String.format("Pantalla %s", mDescribable.getPantalla()));
        txtCamara.setText(String.format("Camara %s", mDescribable.getCam_tras()));
        //txtFlash.setText(String.format("Flash %s", mDescribable.getFlash()));
        txtCamaraFron.setText(String.format("Camara Fro %s", mDescribable.getCam_frontal()));
        txtMemoria.setText(String.format("Memoria %s", mDescribable.getMemoria()));
        //txtBateria.setText(String.format("Bateria %s", mDescribable.getBateria()));

    }

    private void setUiPageViewController() {

        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.btn_next:
                viewPager.setCurrentItem((viewPager.getCurrentItem() < dotsCount)
                        ? viewPager.getCurrentItem() + 1 : 0);
                break;

            case R.id.btn_finish:
                finish();
                break;
        }*/
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

        if (position + 1 == dotsCount) {
            //btnNext.setVisibility(View.GONE);
            //btnFinish.setVisibility(View.VISIBLE);
        } else {
            //btnNext.setVisibility(View.VISIBLE);
            //btnFinish.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
