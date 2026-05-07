import { Transmission } from '../enum/transmission';
import { TypeIst } from '../enum/type-ist';
import { Prevention } from './prevention';
import { Symptome } from './Symptome';
import { Traitement } from './traitement';

export interface Ist {
  id: number;
  nom: string;
  gravite: number;
  incidence: number;
  symptomes: Symptome[];
  shortDescription: string;
  longDescription: string;
  typeIst: TypeIst;
  traitements: Traitement[];
  preventions: Prevention[];
  transmissions: Transmission[];
}
