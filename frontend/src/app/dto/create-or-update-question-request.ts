export interface CreateOrUpdateQuestionReponseRequest {
  reponse: string;
  correct: boolean;
}

export interface CreateOrUpdateQuestionRequest {
  question: string;
  reponses: CreateOrUpdateQuestionReponseRequest[];
}
