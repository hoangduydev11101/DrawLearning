package com.draw.leduy.ezdraw.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.draw.leduy.ezdraw.BaseActivity;
import com.draw.leduy.ezdraw.R;
import com.draw.leduy.ezdraw.view.CanvasView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class HomeActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.view_canvas)
    CanvasView mCanvasView;

    @Optional
    @OnClick(R.id.tv_clear)
    void clearPathOnClick() {
        clearPath();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

    }

    private void clearPath() {
        if (mCanvasView != null)
            mCanvasView.clearCanvas();
    }
}
