import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.development";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {MultimediaModel} from "../shared/data-models/Multimedia.model";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";

@Injectable({
    providedIn: 'root'
})
export class MultimediaService {

    baseUrl = environment.baseUrl;

    constructor(private http: HttpClient, private sanitizer: DomSanitizer) {
    }

    convertToSafeUrl(base64: string, contentType: string): SafeResourceUrl {
        const dataUrl = `data:${contentType};base64,${base64}`;
        return this.sanitizer.bypassSecurityTrustResourceUrl(dataUrl);
    }

    convertSafeUrlToBase64(safeUrl: SafeResourceUrl): string | null {
        const url = safeUrl.toString();
        const base64StartIndex = url.indexOf(';base64,');
        if (base64StartIndex !== -1) {
            return url.slice(base64StartIndex + ';base64,'.length);
        }
        return null;
    }

    public addMultimediaPhoto(title: string, file: File): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('title', title);
        formData.append('file', file, file.name);

        const headers = new HttpHeaders();
        headers.append('Content-Type', 'multipart/form-data');

        return this.http.post(`${this.baseUrl}multimedia/photos/add`, formData, {headers});
    }

    public addMultimediaVideo(title: string, file: File): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('title', title);
        formData.append('file', file, file.name);

        const headers = new HttpHeaders();
        headers.append('Content-Type', 'multipart/form-data');

        return this.http.post(`${this.baseUrl}multimedia/videos/add`, formData, {headers});
    }

    public addMultimediaTextPost(multimedia: MultimediaModel):Observable<any> {
        return this.http.post(this.baseUrl + 'multimedia/save/data/text', multimedia)
    }

    public addMultimediaSharedPost(multimedia: MultimediaModel): Observable<any> {
        return this.http.post(this.baseUrl + 'multimedia/save/data/shared', multimedia)
    }

    public addMultimediaMeta(multimedia: MultimediaModel): Observable<any> {
        return this.http.put(this.baseUrl + 'multimedia/save/data', multimedia);
    }

    public updatePostCaption(id: any, data: any): Observable<any> {
        return this.http.put(this.baseUrl + 'multimedia/update/caption/'+id, {
            title: data.title,
            sharedUserCaption: data.sharedUserCaption
        });
    }

    public getAllMultimedia(): Observable<any> {
        return this.http.get(this.baseUrl + 'multimedia/get/all');
    }

    public getMultimediaById(id: string): Observable<any> {
        return this.http.get(this.baseUrl + 'multimedia/get/id/' + id);
    }

    public getMultimediaMetaById(id: string): Observable<any> {
        return this.http.get(this.baseUrl + 'multimedia/get/id/' + id);
    }

    public getMultimediaByUser(id: string): Observable<any> {
        return this.http.get(this.baseUrl + 'multimedia/get/user/' + id);
    }

    public getMultimediaByChannel(id: string): Observable<any> {
        return this.http.get(this.baseUrl + 'multimedia/get/channel/' + id);
    }

    public getMultimediaByChat(id: string): Observable<any> {
        return this.http.get(this.baseUrl + 'multimedia/get/chat/' + id);
    }

    public deleteMultimedia(id: string): Observable<any> {
        return this.http.delete(this.baseUrl + 'multimedia/delete/' + id);
    }
}
