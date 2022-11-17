import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ServiceMethodService } from './service-method.service';

describe('ServiceMethodService', () => {
  let service: ServiceMethodService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        HttpClient,
        ServiceMethodService
      ]
    }).compileComponents();
    service = TestBed.inject(ServiceMethodService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
