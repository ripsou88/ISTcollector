export type TypePrevention = 'medical' | 'barriere' | 'comportement';

export const TypePreventionEmoji: Record<TypePrevention, { emoji: string; label: string }> = {
  medical: { emoji: '💊', label: 'Médical' },
  barriere: { emoji: '🛡️', label: 'Barrière' },
  comportement: { emoji: '👥', label: 'Comportemental' },
};
