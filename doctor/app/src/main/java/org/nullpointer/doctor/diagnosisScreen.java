package org.nullpointer.doctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class diagnosisScreen extends Activity {

    Bundle detailsImported;
    String mailIdImportedString = "",userIdImportedString = "",doctorNameImportedString = "";
    Button checkMedicalHistoryButton;
    MedicalHistoryFromJson medicalHistoryFromJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_screen);

        detailsImported = getIntent().getExtras();
        mailIdImportedString = detailsImported.getString("MAIL_ID_CLIENT");
        userIdImportedString = detailsImported.getString("DOCTOR_ID");
        doctorNameImportedString = detailsImported.getString("DOCTOR_NAME");
        checkMedicalHistoryButton= (Button) findViewById(R.id.checkMedicalHistory);
        medicalHistoryFromJson = new MedicalHistoryFromJson(this, mailIdImportedString);
    }


    public void goToMedicalHistoryClientScreen(View view) {

        medicalHistoryFromJson.goToMedicalHistoryClientScreen();

    }

    public void enterDetailsOfDiagnosis(View view) {
        Intent intent = new Intent(this, enterDiagnosisDetails.class);
        intent.putExtra("MAIL_ID_CLIENT", mailIdImportedString);
        intent.putExtra("DOCTOR_ID",userIdImportedString);
        intent.putExtra("DOCTOR_NAME",doctorNameImportedString);
        startActivity(intent);
    }

    public void endSession(View view){
        Intent intent = new Intent(this,HomeScreenDoctor.class);
        intent.putExtra("DOCTOR_ID",userIdImportedString);
        intent.putExtra("DOCTOR_NAME",doctorNameImportedString);
        startActivity(intent);
    }
}
