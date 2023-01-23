import { RecordsService } from './../../service/records.service';
import { FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { EMPTY, catchError, debounceTime, filter, map, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-list-records',
  templateUrl: './list-records.component.html',
  styleUrls: ['./list-records.component.sass']
})
export class ListRecordsComponent {

  searchField = new FormControl();
  message: string = "";

  constructor(private recordsService: RecordsService) { }

  foundRecords$ = this.searchField.valueChanges
    .pipe(
      debounceTime(200),
      filter((enteredValue) => enteredValue.length >= 1 && enteredValue.length <= 2),
      switchMap((enteredValue) => this.recordsService.findByRegion(enteredValue)),
      // tap((retornoAPI) => console.log(retornoAPI)),
      map(retornoAPI => retornoAPI.content ?? []),
      catchError( () => {
        this.message = "Ops, ocorreu um erro. Por favor, recarregue a aplicação.";
        return EMPTY;
      })
    );

}
