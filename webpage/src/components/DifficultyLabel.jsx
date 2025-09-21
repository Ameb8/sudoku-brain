const difficultyStyles = {
    EASY: {
        color: 'green',
        label: 'Easy',
    },
    MEDIUM: {
        color: 'goldenrod',
        label: 'Medium',
    },
    HARD: {
        color: 'orange',
        label: 'Hard',
    },
    EXPERT: {
        color: 'red',
        label: 'Expert',
    },
};

export default function DifficultyLabel({ difficulty }) {
    const style = difficultyStyles[difficulty];

    if (!style) {
        return null;
    }

    return (
        <span
            style={{
                color: style.color,
                fontWeight: 'bold',
                textTransform: 'uppercase',
                fontSize: '0.9rem',
                letterSpacing: '0.05em',
            }}
        >
      {style.label}
    </span>
    );
}
