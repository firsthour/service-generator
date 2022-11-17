import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ServiceMethod } from '../model/service-method';
import { Observable } from 'rxjs';
import { JavaClass } from '../model/java-class';

@Injectable()
export class ServiceMethodService {

  private url: string;

  constructor(private http: HttpClient) {
    this.url = '/api/services';
  }

  public generate(serviceMethod: ServiceMethod) {
    return this.http.post<JavaClass>(this.url + '/generate', serviceMethod);
  }

  public save(serviceMethod: ServiceMethod) {
    return this.http.post<JavaClass>(this.url + '/save', serviceMethod);
  }

  public findAll(): Observable<ServiceMethod[]> {
    return this.http.get<ServiceMethod[]>(this.url);
  }
}
