import {Component, OnInit} from '@angular/core';

import {RouterLink} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment.development";
import {ApiResponse} from "../../utils/ApiResponse";


@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit{
  apiUrl = environment.apiUrl;
  text = '';

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.admin();
  }

  admin() {
    this.http.get<ApiResponse>(this.apiUrl + 'admin').subscribe((data: ApiResponse) => {
      console.log(data.message)
    });
  }
}
