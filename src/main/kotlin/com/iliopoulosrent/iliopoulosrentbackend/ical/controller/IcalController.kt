package com.iliopoulosrent.iliopoulosrentbackend.ical.controller

import com.iliopoulosrent.iliopoulosrentbackend.ical.`object`.IcalEvent
import com.iliopoulosrent.iliopoulosrentbackend.ical.service.IcalService
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller("/api/ical")
class IcalController(var icalService: IcalService) {

    @GetMapping
    fun exportCalenderFile(): ResponseEntity<Resource> {
        val calendarByte =
            icalService.createCalendar(listOf(IcalEvent("Test",1615956275000L, 1615959875000L))).toString().toByteArray()
        val resource: Resource = ByteArrayResource(calendarByte)
        val header = HttpHeaders()
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mycalendar.ics")
        header.add("Cache-Control", "no-cache, no-store, must-revalidate")
        header.add("Pragma", "no-cache")
        header.add("Expires", "0")
        return ResponseEntity.ok().headers(header).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource)
    }

    @PostMapping
    fun importCalenderFile(): ResponseEntity<Void> {
        val calendarByte =
            icalService.createCalendar(listOf(IcalEvent("Test",1615956275000L, 1615959875000L))).toString().toByteArray()
        val resource: Resource = ByteArrayResource(calendarByte)
        val header = HttpHeaders()
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mycalendar.ics")
        header.add("Cache-Control", "no-cache, no-store, must-revalidate")
        header.add("Pragma", "no-cache")
        header.add("Expires", "0")
        return ResponseEntity.ok().headers(header).contentType(MediaType.APPLICATION_OCTET_STREAM).body(null)
    }
}