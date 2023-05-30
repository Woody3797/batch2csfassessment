import { Component, OnInit, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Bundle } from '../model';
import { UtilityService } from '../utility.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-view2',
  templateUrl: './view2.component.html',
  styleUrls: ['./view2.component.css']
})
export class View2Component implements OnInit {

    utility = inject(UtilityService)
    activatedRoute = inject(ActivatedRoute)

    bundle$!: Observable<Bundle>
    bundleId = ''

    ngOnInit(): void {
        this.bundleId = this.activatedRoute.snapshot.params['bundleId']
        this.bundle$ = this.utility.getBundleByBundleId(this.bundleId)
    }









}
