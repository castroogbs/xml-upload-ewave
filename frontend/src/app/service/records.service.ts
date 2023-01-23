import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { APIResponseObject } from '../models/APIResponseObject';

@Injectable({
  providedIn: 'root'
})
export class RecordsService {

  readonly API: string = "http://localhost:8080/api/v1";

  constructor(private http: HttpClient) { }

  findByRegion(enteredValue: string): Observable<APIResponseObject> {
    const params = new HttpParams()
      .append("size", 10)
      .append("page", 1)

    return this.http.get<APIResponseObject>(this.API + "/records/region/" + enteredValue, { params });
  }
}
