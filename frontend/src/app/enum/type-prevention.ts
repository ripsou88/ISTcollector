export enum TypePrevention {
  Medical = 'MEDICAL',
  Barriere = 'BARRIERE',
  Comportemental = 'COMPORTEMENTAL',
}

export const TypePreventionEmoji: Record<TypePrevention, { emoji: string, label: string }> = {
  [TypePrevention.Medical]: { emoji: '💊', label: 'Médical' },
  [TypePrevention.Barriere]: { emoji: '🛡️', label: 'Barrière' },
  [TypePrevention.Comportemental]: { emoji: '👥', label: 'Comportemental' },
};