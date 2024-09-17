import {Injectable, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.development";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GetDataService implements OnInit {
  apiUrl = environment.apiUrl
  constructor(private http: HttpClient) {
  }

  ngOnInit() {
  }

  getAllergies(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/allergies`);
  }

  getDiets(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/diets`);
  }
}
