package org.example.ticketmaster

import android.app.Application
import org.example.ticketmaster.di.initKoin

class TicketMasterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}