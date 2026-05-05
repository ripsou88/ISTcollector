export enum Transmission {
  Orale = 'ORALE',
  Sexuelle = 'SEXUELLE',
  Contact_Sanguin = 'CONTACT_SANGUIN',
  Contact_Direct = 'CONTACT_DIRECT',
  Materno_Foetale = 'MATERNO_FOETALE',
}

export const TransmissionEmoji: Record<Transmission, { emoji: string, label: string }> = {
  [Transmission.Orale]: { emoji: '👄', label: 'Orale' },
  [Transmission.Sexuelle]: { emoji: '🍆', label: 'Sexuelle' },
  [Transmission.Contact_Sanguin]: { emoji: '💉', label: 'Contact sanguin' },
  [Transmission.Contact_Direct]: { emoji: '🤝', label: 'Contact direct' },
  [Transmission.Materno_Foetale]: { emoji: '👶', label: 'Materno-fœtale' },
};