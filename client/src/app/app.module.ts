import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http'
import { View1Component } from './view1/view1.component';
import { View0Component } from './view0/view0.component';
import { View2Component } from './view2/view2.component';
import { UtilityService } from './utility.service';

@NgModule({
  declarations: [
    AppComponent,
    View1Component,
    View0Component,
    View2Component,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [UtilityService],
  bootstrap: [AppComponent]
})
export class AppModule { }
