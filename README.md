# mhpSourceProvider
Source provider library for MH Player

#Usage
1. Pull library project
2. Add dependency: 'compile project(':sourceprovider')'
3. Add your provider service
'''xml
        <service
            android:name=".<YOUR_SERVICE_NAME>"
            android:enabled="true"
            android:exported="true"
            android:label="<SERVICE_LABEL>"
            android:process=":remote">
            <intent-filter>
                <action android:name="com.msc.player.ISourceProvider" />
            </intent-filter>
        </service>
'''
