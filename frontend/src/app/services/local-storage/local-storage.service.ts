import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() { }

  saveObject(object:any, name: string): void {
    localStorage.setItem(name, JSON.stringify(object));
  }

  retrieveObject(name: string): any {
    let object = localStorage.getItem(name);

    if(object){
      return JSON.parse(object);
    }

    return null;
  }

  deleteObject(name: string): void {
    localStorage.removeItem(name);
  }

  clearStorage(): void {
    localStorage.clear();
  }
}
