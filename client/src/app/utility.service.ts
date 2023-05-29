import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UtilityService {

    constructor() { }

    http = inject(HttpClient)


    upload1(name: string, title: string, comments: string, archive: File): Observable<string> {
        var formData = new FormData();
        formData.set('name', name)
        formData.set('title', title)
        formData.set('comments', comments)
        formData.set('archive', archive)
        // formData.forEach(f => console.info(f))

        return this.http.post<string>('http://localhost:8080/upload', formData)
    }



}
