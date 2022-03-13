import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  isAdmin: boolean=true;

  constructor(private route: ActivatedRoute) { }

    ngOnInit() {
    }

    logout(): void{
    }
}
