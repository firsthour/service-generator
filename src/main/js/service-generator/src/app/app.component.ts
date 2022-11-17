import { Component } from '@angular/core';
import { ServiceMethod } from './model/service-method';
import { ServiceMethodService } from './service/service-method.service';
import { MessageService } from 'primeng/api';
import { QueryParameter } from './model/query-parameter';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [MessageService]
})

export class AppComponent {
  methods: string[];
  serviceMethod: ServiceMethod;
  output!: string;
  newQueryParameterName!: string;
  queryParameterTypes: string[];
  newQueryParameterType: string;
  displayLoadDialog = false;
  loadedServiceMethods: ServiceMethod[] = [];

  constructor(
      private service: ServiceMethodService,
      private messageService: MessageService) {
    
    this.methods = [
      'GET', 'POST'
    ];
    this.queryParameterTypes = [
      'String', 'boolean', 'int'
    ]

    this.newQueryParameterType = this.queryParameterTypes[0];

    this.serviceMethod = new ServiceMethod();
    this.serviceMethod.method = this.methods[0];
    this.serviceMethod.path = this.serviceMethod.method.toLowerCase();

    this.generate();
  }

  generate() {
    this.service.generate(this.serviceMethod)
      .subscribe({
        next: (result) => this.output = result.code,
        error: (error) => this.messageService.add({severity:'error', summary: 'Error', detail: 'Service is down!'})
    });
  }

  dropdownChange(event: any) {
    this.serviceMethod.path = event.value.toLowerCase();
    this.generate();
  }

  showClipboardSuccess() {
    this.messageService.add({severity:'success', summary: 'Success', detail: 'Copied'});
  }

  addQueryParameter() {
    const newQueryParamater: QueryParameter = {
      name: this.newQueryParameterName,
      type: this.newQueryParameterType,
    };
    this.serviceMethod.queryParameters.push(newQueryParamater);
    this.newQueryParameterName = '';
    this.generate();
  }

  removeQueryParameter(index: number) {
    if(index >= this.serviceMethod.queryParameters.length) {
      this.messageService.add({severity:'error', summary: 'Error', detail: 'Parameter not found'});
      return;
    }
    this.serviceMethod.queryParameters.splice(index, 1);
    this.generate();
  }

  save() {
    this.service.save(this.serviceMethod)
      .subscribe({
        next: (result) => this.messageService.add({severity:'success', summary: 'Success', detail: 'Saved'}),
        error: (error) => this.messageService.add({severity:'error', summary: 'Error', detail: 'Service is down!'})
    });
  }

  showLoadDialog() {
    this.service.findAll().subscribe(data => {
      this.loadedServiceMethods = data;
    });
    this.displayLoadDialog = true;
  }

  loadMethod(method: ServiceMethod) {
    this.displayLoadDialog = false;
    this.serviceMethod = method;
  }
}
