import { Component, OnInit, inject } from '@angular/core';
import { UtilityService } from '../utility.service';
import { Observable } from 'rxjs';
import { Bundle } from '../model';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component implements OnInit {

    utility = inject(UtilityService)

    bundles$!: Observable<Bundle[]>
    bundleId = ''

    ngOnInit(): void {
        this.bundles$ = this.utility.getBundles()
    }
}
