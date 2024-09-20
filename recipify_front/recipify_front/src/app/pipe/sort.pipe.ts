import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'sort',
  standalone: true
})
export class SortPipe implements PipeTransform {

  transform(array: any[], sortColumn: string, sortDirection: 'asc' | 'desc'): any[] {
    if (!array || !sortColumn) {
      return array;
    }

    return [...array].sort((a, b) => {
      let comparison = 0;
      if (a[sortColumn] > b[sortColumn]) {
        comparison = 1;
      } else if (a[sortColumn] < b[sortColumn]) {
        comparison = -1;
      }
      return sortDirection === 'asc' ? comparison : -comparison;
    });
  }

}
