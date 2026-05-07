import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'istNameFormat',
})
export class IstNameFormatPipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    if (!value) {
      return '';
    }

    return value
      .replaceAll('_', ' ')
      .split(' ')
      .map((word) => this.capitalize(word))
      .join(' ');
  }

  private capitalize(word: string) {
    return word.charAt(0).toUpperCase() + word.slice(1);
  }
}
