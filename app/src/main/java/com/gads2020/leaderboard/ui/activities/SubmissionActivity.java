package com.gads2020.leaderboard.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.gads2020.leaderboard.Constants;
import com.gads2020.leaderboard.R;
import com.gads2020.leaderboard.api.APIClient;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmissionActivity extends AppCompatActivity {

    private EditText edtProjectLink;
    private EditText edtEmailAddress;
    private EditText edtLastName;
    private EditText edtFirstName;
    private MaterialButton btnSubmit;
    private AlertDialog dialog;
    private ProgressBar mProgressBar;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String projectLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        edtFirstName = findViewById(R.id.edt_first_name);
        edtLastName = findViewById(R.id.edt_last_name);
        edtEmailAddress = findViewById(R.id.edt_email_address);
        edtProjectLink = findViewById(R.id.edt_project_link);
        btnSubmit = findViewById(R.id.btn_submit);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstName = edtFirstName.getText().toString().trim();
                lastName = edtLastName.getText().toString().trim();
                emailAddress = edtEmailAddress.getText().toString().trim();
                projectLink = edtProjectLink.getText().toString().trim();

                // Ensure all the fields are provided with correct values
                if(
                        firstName.isEmpty()
                                || lastName.isEmpty()
                                || emailAddress.isEmpty()
                                || projectLink.isEmpty()
                ) {
                    Toast.makeText(SubmissionActivity.this, getResources().getString(R.string.fill_required_fields), Toast.LENGTH_SHORT).show();
                } else {
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    ConfirmDialog confirmDialog = new ConfirmDialog(SubmissionActivity.this);
                    confirmDialog.displayDialog(viewGroup);
                }
            }
        });
    }

    class ConfirmDialog extends AlertDialog.Builder {
        private LinearLayout confirmSection;
        private LinearLayout resultSection;
        private ImageView mResultIcon;
        private TextView mResultText;

        ConfirmDialog(@NonNull Context context) {
            super(context);
        }

        void displayDialog(ViewGroup viewGroup) {
            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.custom_alert_dialog, viewGroup, false);
            MaterialButton btnConfirm = (MaterialButton) dialogView.findViewById(R.id.btn_confirm);
            final ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btn_close);
            mResultIcon = (ImageView) dialogView.findViewById(R.id.result_icon);
            mResultText = (TextView) dialogView.findViewById(R.id.result_text);
            mProgressBar = (ProgressBar) dialogView.findViewById(R.id.progress_bar);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    btnClose.setVisibility(View.GONE);
                    submitProject();
                }
            });

            confirmSection = (LinearLayout) dialogView.findViewById(R.id.confirm_section);
            resultSection = (LinearLayout) dialogView.findViewById(R.id.result_section);
            this.setView(dialogView);
            dialog = this.create();
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Project submission
        private void submitProject() {
            Call<JsonElement> submitCall = APIClient.getInstance(Constants.SUBMISSION_BASE_URL)
                    .submitProject(
                            firstName,
                            lastName,
                            emailAddress,
                            projectLink
                    );

            submitCall.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                    mProgressBar.setVisibility(View.GONE);
                    confirmSection.setVisibility(View.GONE);
                    resultSection.setVisibility(View.VISIBLE);
                    if(response.isSuccessful()) {
                        Toast.makeText(SubmissionActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        mResultIcon.setImageResource(R.drawable.ic_done);
                        mResultText.setText(getResources().getString(R.string.submission_successful));
                    } else {
                        mResultIcon.setImageResource(R.drawable.ic_warning);
                        mResultText.setText(getResources().getString(R.string.submission_not_successful));
                        Toast.makeText(SubmissionActivity.this, "Error : " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                    mProgressBar.setVisibility(View.GONE);
                    confirmSection.setVisibility(View.GONE);
                    resultSection.setVisibility(View.VISIBLE);
                    mResultIcon.setImageResource(R.drawable.ic_warning);
                    mResultText.setText(getResources().getString(R.string.submission_not_successful));
                    Toast.makeText(SubmissionActivity.this, "Failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
