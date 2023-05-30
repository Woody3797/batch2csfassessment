import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Bundle } from './model';

@Injectable({
    providedIn: 'root'
})
export class UtilityService {

    http = inject(HttpClient)

    bundleId = ''


    upload(name: string, title: string, comments: string, archive: File): Observable<any> {
        const formData = new FormData();
        formData.set('name', name)
        formData.set('title', title)
        formData.set('comments', comments)
        formData.set('archive', archive)

        return this.http.post<any>('http://localhost:8080/upload', formData)
    }

    getBundleByBundleId(bundleId: string): Observable<Bundle> {
        const params = new HttpParams().set('bundleId', bundleId)

        return this.http.get<Bundle>('http://localhost:8080/bundle/' + bundleId)
    }

    getBundles(): Observable<Bundle[]> {

        return this.http.get<Bundle[]>('http://localhost:8080/bundles')
    }

}
