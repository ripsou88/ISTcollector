import { Reponse } from './reponse';

export interface Question {
  id: number;
  question: string;
  reponses: Reponse[];
}
