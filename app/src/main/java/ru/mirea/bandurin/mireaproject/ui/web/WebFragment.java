package ru.mirea.bandurin.mireaproject.ui.web;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import ru.mirea.bandurin.mireaproject.R;
import ru.mirea.bandurin.mireaproject.databinding.FragmentGalleryBinding;
import ru.mirea.bandurin.mireaproject.databinding.FragmentWebBinding;
import ru.mirea.bandurin.mireaproject.ui.gallery.GalleryViewModel;

public class WebFragment extends Fragment {

    private FragmentWebBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WebViewModel galleryViewModel =
                new ViewModelProvider(this).get(WebViewModel.class);

        binding = FragmentWebBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final WebView webView = binding.webView;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://google.com");
        WebViewClient webViewClient = new WebViewClient() {

            @SuppressWarnings("deprecation") @Override

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                return true;

            }



            @TargetApi(Build.VERSION_CODES.N) @Override

            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                view.loadUrl(request.getUrl().toString());

                return true;

            }


        };
        webView.setWebViewClient(webViewClient);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}