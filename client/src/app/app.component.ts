import {Component, OnInit} from '@angular/core';
import {ThemeService} from "./services/theme.service";
import {WebSocketService} from "./services/web-socket.service";
import {MultimediaService} from "./services/multimedia.service";
import {EmployeesService} from "./services/employees.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'client';
  employeeDataStore:any;
  employee: any;
  userId:any;

  constructor(public themeService: ThemeService, private webSocketService: WebSocketService, public multimediaService: MultimediaService, private employeeService: EmployeesService) {

  }

  ngOnInit(): void {
    this.loadAllUsers();
    localStorage.setItem('sender','66105c4922d9fd0f2042909f')
    this.userId = localStorage.getItem('sender')

    // Establish WebSocket connection
    this.webSocketService.connect('ws://localhost:4200/ws');

    this.webSocketService.getConnectionStatus().subscribe((status: boolean) => {
      console.log('WebSocket connection status:', status);
    }, (error: any) => {
      console.error('WebSocket connection error:', error);
    });
  }

  ngOnDestroy(): void {
    this.webSocketService.disconnect();
  }

  loadAllUsers() {
    this.employeeService.getAllEmployees().subscribe(data =>{
      this.employeeDataStore = data;
      this.getUser(this.employeeDataStore);

      // convert base64 images to safe urls
      this.employeeDataStore.forEach((emp:any) => {
        emp.photo = this.multimediaService.convertToSafeUrl(emp.photo, 'image/jpeg');
      })
    }, error => {
      console.log(error)
    })
  }

  getUser(data:any) {
    data.forEach((emp:any) => {
      if (emp.id == this.userId) {
        this.employee = [emp];
      }
    })
  }

  toggleTheme() {
    this.themeService.toggleTheme();
  }
}
