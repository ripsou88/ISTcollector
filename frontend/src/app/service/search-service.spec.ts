import { TestBed } from '@angular/core/testing';
import { SearchService } from './search-service';

describe('SearchService', () => {
  let service: SearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SearchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should set and get search value', () => {
    service.setSearch('vih');
    expect(service.getSearch()).toBe('vih');
  });

  it('should update observable value', (done) => {
    service.search$.subscribe(value => {
      if (value === 'syphilis') {
        expect(value).toBe('syphilis');
        done();
      }
    });

    service.setSearch('syphilis');
  });
});