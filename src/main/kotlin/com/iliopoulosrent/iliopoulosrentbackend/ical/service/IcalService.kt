package com.iliopoulosrent.iliopoulosrentbackend.ical.service

import com.iliopoulosrent.iliopoulosrentbackend.ical.`object`.IcalEvent
import net.fortuna.ical4j.model.Calendar

interface IcalService {
    fun createCalendar(icalEvents: List<IcalEvent>): Calendar
}