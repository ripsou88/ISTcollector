export type Transmission = 'orale' | 'sexuelle' | 'sang' | 'direct' | 'materno';

export const TransmissionEmoji: Record<Transmission, { emoji: string; label: string }> = {
  orale: { emoji: '👄', label: 'Orale' },
  sexuelle: { emoji: '🍆', label: 'Sexuelle' },
  sang: { emoji: '💉', label: 'Contact sanguin' },
  direct: { emoji: '🤝', label: 'Contact direct' },
  materno: { emoji: '👶', label: 'Materno-fœtale' },
};
