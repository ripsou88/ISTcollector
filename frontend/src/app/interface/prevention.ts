import { TypePrevention } from '../enum/type-prevention';

export interface Prevention {
  id: number;
  nom: string;
  typePrevention: TypePrevention;
}
