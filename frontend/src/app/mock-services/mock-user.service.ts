import { Injectable } from "@angular/core";
import { throwError } from "rxjs";
import { User, UserService } from "../services/user/user.service";

@Injectable({
  providedIn: 'root',
})
export class MockUserService extends UserService {
   private existingUser: any = {
    id: "id",
    firstName: "Ajsa",
    lastName: "Hajradinovic Ajsa",
    email: "ajsah@projecthub.com",
    password: ""
   }

   login(email: string, password: string, errorHandler: any): any {
    if (email === this.existingUser.email && password !== this.existingUser.password)
     errorHandler({status: 400});
   }
}