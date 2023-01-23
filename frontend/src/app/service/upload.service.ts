import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  readonly API: string = "http://localhost:8080/api/v1";

  constructor(private http: HttpClient) { }

  public upload(file: File): Observable<any> {
    const formData = new FormData();
    formData.append("file", file);

    return this.http.post(this.API+"/upload/file", formData, {
      observe: "events",
      reportProgress: true
    });
  }
}
