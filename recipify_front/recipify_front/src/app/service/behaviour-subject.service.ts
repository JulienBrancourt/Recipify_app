import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UpdateService {
  private updateSubject = new BehaviorSubject<void>(undefined);
  update$ = this.updateSubject.asObservable();

  triggerUpdate() {
    this.updateSubject.next();
  }
}
