import { Component, OnInit, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Bundle } from '../model';
import { UtilityService } from '../utility.service';

@Component({
  selector: 'app-view2',
  templateUrl: './view2.component.html',
  styleUrls: ['./view2.component.css']
})
export class View2Component implements OnInit {

    utility = inject(UtilityService)

    
    bundle$!: Observable<Bundle>

    ngOnInit(): void {
        
    }









}
