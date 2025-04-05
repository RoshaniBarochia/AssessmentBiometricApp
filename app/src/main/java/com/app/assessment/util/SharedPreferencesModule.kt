package com.app.assessment.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.app.assessment.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.IOException
import java.security.GeneralSecurityException
import javax.inject.Singleton


const val SECURE_PREFS_NAME = "secure_prefs"

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context?): SharedPreferences {
        try {
            val masterKey = createMasterKey(context!!)
            return EncryptedSharedPreferences.create(
                context,
                SECURE_PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: GeneralSecurityException) {
            throw RuntimeException("Failed to create EncryptedSharedPreferences", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to create EncryptedSharedPreferences", e)
        }
    }
}
fun createMasterKey(context: Context): MasterKey {
    return MasterKey.Builder(context, BuildConfig.MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
}
