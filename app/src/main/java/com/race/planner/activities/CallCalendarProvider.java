/**
 * DEPRECATED
 */

//package com.race.planner;
//
//
//import android.accounts.Account;
//import android.accounts.AccountManager;
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.provider.CalendarContract.Calendars;
//import android.provider.CalendarContract.Events;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
///**
// * This whole class is deprecated.
// * Could not implement app in its current form with the internal android calendar API.
// */
//public class GenerateTrainingPlan extends AppCompatActivity
//{
//    private static final String TAG = GenerateTrainingPlan.class.getName();
//    List<String> namesOfCalendars = new ArrayList<>();
//    Racer racer;
//    RacerInfo racerInfo;
//    String calName;
//    Long id;
//    private boolean calCreated;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_generate_training_plan);
//
//        // grab data from previous activity
//        racer  = getIntent().getExtras().getParcelable(GlobalVariables.RACER_INFO_ID);
//        racerInfo  = getIntent().getExtras().getParcelable(GlobalVariables.RACER_INFO_ID);
//        calCreated = getIntent().getExtras().getBoolean(GlobalVariables.CALENDAR_CREATED_ID);
//
//        List<CalendarInfo> calendars = getCalendarList();
//        for (CalendarInfo c : calendars)
//        {
//            Log.i(TAG, c.name + " " + c.id);
//        }
//
//        if (calCreated)
//        {
//            TextView textView = (TextView) findViewById(R.id.textView);
//            textView.setText("Your race type is: " + racer.raceType + '\n'
//                    + "Your experience level is: " + racer.experienceLevel + '\n'
//                    + "The date of your race is: " + racer.year + "/" + racer.month + "/" + racer.day);
//            textView.setText("Your race type is: " + racerInfo.raceType + '\n'
//                    + "Your experience level is: " + racerInfo.experienceLevel + '\n'
//                    + "The date of your race is: " + racerInfo.year + "/" + racerInfo.month + "/" + racerInfo.day);
//            Spinner spinner = (Spinner) findViewById(R.id.spinner_calendar_select);
//            //spinner.setVisibility(View.GONE);
//            id = getCalendar("race-planner");
//            Log.i(TAG, "calCreated called...");
//            generateCalendarSelectSpinner(calendars);
//            generateCalendarConfirmButton();
//        }
//        else
//        {
//
//
//            // create spinner and add calendars to it for selection
//            generateCalendarSelectSpinner(calendars);
//
//            // button generation
//            generateCalendarConfirmButton();
//        }
//    }
//
//    public List<CalendarInfo> getCalendarList()
//    {
//        List<CalendarInfo> result = new ArrayList<>();
//        namesOfCalendars = new ArrayList<>();
//        try
//        {
//            // See "Querying a Calendar"
//            // https://developer.android.com/guide/topics/providers/calendar-provider.html
//            String[] projection = new String[]{
//                    Calendars._ID,
//                    Calendars.NAME,
//                    Calendars.ACCOUNT_NAME,
//                    Calendars.ACCOUNT_TYPE
//            };
//
//            // ContentResolver receives a URI to a specific Content Provider
//            // Content Providers provide an interface to query content
//            // Cursors use ContentResolvers to iterate through
//            ContentResolver cr = getContentResolver();
//            Uri uri = Calendars.CONTENT_URI;
//            Cursor calCursor;
//            calCursor = cr.query(uri, projection, null, null, null);
//
//            while (calCursor.moveToNext())
//            {
//                result.add(new CalendarInfo(calCursor.getLong(0), calCursor.getString(1)));
//                namesOfCalendars.add(calCursor.getString(1));
//            }
//            calCursor.close();
//        } catch (SecurityException e)
//        {
//            Log.e(TAG, "Permission Denied. Did you set permissions?");
//        }
//        return result;
//    }
//
//
//    /**
//     *  Generates a list of CalendarInfo objects to be used if the user does not want to make a new
//     *  calendar for their training plan.
//     */
//    public long getCalendar(String calName)
//    {
//        long ID = -1;
//        try
//        {
//            // See "Querying a Calendar"
//            // https://developer.android.com/guide/topics/providers/calendar-provider.html
//            String[] projection = new String[]{
//                    Calendars._ID,
//                    Calendars.NAME,
//                    Calendars.ACCOUNT_NAME,
//                    Calendars.ACCOUNT_TYPE
//            };
//
//            // ContentResolver receives a URI to a specific Content Provider
//            // Content Providers provide an interface to query content
//            // Cursors use ContentResolvers to iterate through
//            ContentResolver cr = getContentResolver();
//            Uri uri = Calendars.CONTENT_URI;
//            Cursor calCursor = cr.query(uri, projection, null, null, null);
//
//            while (calCursor.moveToNext())
//            {
//                String temp = calCursor.getString(1);
//                if (calCursor.getString(1).equals(calName))
//                {
//                    ID = calCursor.getLong(0);
//                    break;
//                }
//            }
//            calCursor.close();
//        } catch (SecurityException e)
//        {
//            Log.e(TAG, "Permission Denied. Did you set permissions?");
//        }
//        return ID;
//    }
//
//
//    /**
//     * Generates the spinner to select which calendar to export to.
//     */
//    private void generateCalendarSelectSpinner(final List<CalendarInfo> calendars)
//    {
//        // create spinner and add calendars to it for selection
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, namesOfCalendars);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Spinner calendarSelect = (Spinner) findViewById(R.id.spinner_calendar_select);
//        calendarSelect.setAdapter(adapter);
//        calendarSelect.setOnItemSelectedListener(new OnItemSelectedListener()
//        {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//                // set calendar to be edited with training plan
//                switch (parent.getId())
//                {
//                    case R.id.spinner_calendar_select:
//                    {
//                        TextView textView = (TextView) findViewById(R.id.textView);
//                        textView.setText(calendars.get(position).id + " | " + calendars.get(position).name);
//                        //calID = calendars.get(position).id;
//                        calName = namesOfCalendars.get(position);
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent)
//            {
//                // nothing to see here
//            }
//        });
//    }
//
//    /**
//     * Generates button that when selected will create the events.
//     */
//    private void generateCalendarConfirmButton()
//    {
//        Button confirmCalendarButton = (Button) findViewById(R.id.confirmCalendar);
//        confirmCalendarButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                id = getCalendar(calName);
//                // create dummy event for testing
//                new MakeTrainingPlanTask(racer, id).execute();
//                new MakeTrainingPlanTask(racerInfo, id).execute();
//
//            }
//        });
//    }
//
//    /**
//     * This creates a new event for the selected calendar. This should be put on an asyncronous
//     * task for optimization.
//     * @param curActivity current activity
//     * @param racer the date of the event
//     * @param racerInfo the date of the event
//     */
//
//    /**
//     * holds the info of the calendars on the phone.
//     */
//    private class CalendarInfo
//    {
//        private Long id;
//        private String name;
//        private int color;
//
//        private CalendarInfo(Long i, String n)
//        {
//            this.id = i;
//            this.name = n;
//        }
//    }
//
//    private class MakeTrainingPlanTask extends AsyncTask<Long, Void, Void>
//    {
//        private int year;
//        private int month;
//        private int day;
//        private Long id;
//        private String raceType;
//        private String experienceLevel;
//
//        MakeTrainingPlanTask(Racer racer, Long i)
//        {
//            year = racer.year;
//            month = racer.month;
//            day = racer.day;
//            raceType = racer.raceType;
//            experienceLevel = racer.experienceLevel;
//        MakeTrainingPlanTask(RacerInfo racerInfo, Long i)
//        {
//            year = racerInfo.year;
//            month = racerInfo.month;
//            day = racerInfo.day;
//            raceType = racerInfo.raceType;
//            experienceLevel = racerInfo.experienceLevel;
//            id = i;
//        }
//
//        @Override
//        public Void doInBackground(Long... params)
//        {
//            createEvent(id, racer);
//            createEvent(id, racerInfo);
//            return null;
//        }
//    }
//
//    public void createEvent(Long calID, Racer racer)
//    {
//        Calendar beginTime = Calendar.getInstance();
//        beginTime.set(racer.year, racer.month - 1, racer.day, 0, 0);
//        Calendar endTime = Calendar.getInstance();
//        endTime.set(racer.year, racer.month - 1, racer.day, 0, 0);
//    public void createEvent(Long calID, RacerInfo racerInfo)
//    {
//        Calendar beginTime = Calendar.getInstance();
//        beginTime.set(racerInfo.year, racerInfo.month - 1, racerInfo.day, 0, 0);
//        Calendar endTime = Calendar.getInstance();
//        endTime.set(racerInfo.year, racerInfo.month - 1, racerInfo.day, 0, 0);
//
//        long startMillis = beginTime.getTimeInMillis();
//        long endMillis = endTime.getTimeInMillis();
//
//        ContentResolver cr = getContentResolver();
//        ContentValues values = new ContentValues();
//        values.put(Events.ALL_DAY, true);
//        values.put(Events.DTSTART, startMillis);
//        values.put(Events.DTEND, endMillis);
//        values.put(Events.TITLE, "Test Event");
//        values.put(Events.DESCRIPTION, "Test Description");
//        values.put(Events.CALENDAR_ID, calID);
//        values.put(Events.EVENT_TIMEZONE, "America/Los_Angeles");
//        try
//        {
//            // commented out to avoid unnecessary event additions
//            Uri uri = cr.insert(Events.CONTENT_URI, values);
//
//            // Force a sync
//            Bundle extras = new Bundle();
//            extras.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
//            extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
//            AccountManager am = AccountManager.get(this);
//            Account[] acc = am.getAccountsByType("com.google");
//            Account account = null;
//            if (acc.length>0) {
//                account=acc[0];
//                ContentResolver.requestSync(account, "com.android.calendar", extras);
//                Log.i(TAG, account.toString());
//            }
//
//            Log.i(TAG, calID.toString());
//            Log.i(TAG, "Event Created");
//
//        } catch (SecurityException e)
//        {
//            Log.e(TAG, "Permission Denied");
//        }
//    }
//}
