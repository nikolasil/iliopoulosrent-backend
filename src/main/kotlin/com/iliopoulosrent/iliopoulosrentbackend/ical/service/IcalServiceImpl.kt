package com.iliopoulosrent.iliopoulosrentbackend.ical.service

import com.iliopoulosrent.iliopoulosrentbackend.ical.`object`.IcalEvent
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.Attendee
import net.fortuna.ical4j.model.property.Location
import net.fortuna.ical4j.model.property.Organizer
import net.fortuna.ical4j.model.property.ProdId
import net.fortuna.ical4j.util.RandomUidGenerator
import net.fortuna.ical4j.util.UidGenerator
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Service
class IcalServiceImpl : IcalService {
    override fun createCalendar(icalEvents: List<IcalEvent>): Calendar {
        val icsCalendar = Calendar()

        for (icalEvent in icalEvents) {
            /* Event start and end time in milliseconds */
            val calendarStartTime: java.util.Calendar = GregorianCalendar()
            calendarStartTime.setTimeInMillis(icalEvents[0].start)

            // Time zone info
            val tz = calendarStartTime.getTimeZone()
            val zid = tz.toZoneId()

            /* Generate unique identifier */
            val ug: UidGenerator = RandomUidGenerator()
            val uid = ug.generateUid()

            /* Create the event */
            val eventSummary = "New Year"
            val start = LocalDateTime.ofInstant(calendarStartTime.toInstant(), zid)
            val end = LocalDateTime.ofInstant(Instant.ofEpochMilli(icalEvents[0].end), zid)
            val event = VEvent(
                start,
                end,
                eventSummary
            )
            event.add(uid)

            // Add email addresses as attendees
            val attendee1 = Attendee("danny@example.com")
            val attendee2 = Attendee("jenifer@example.com")
            event.add(attendee1)
            event.add(attendee2)

            // Create an Organizer
            val organizer = Organizer()
            organizer.value = "MAILTO:sender@example.com"
            event.add(organizer)

            /* Create calendar */

            icsCalendar.add(ProdId("-//Ben Fortuna//iCal4j 1.0//EN"))

            // Set the location
            val location = Location("Conference Room")

            // Add the Location to the event
            event.add(location)

            /* Add event to calendar */
            icsCalendar.add(event)
        }

        return icsCalendar
    }
}